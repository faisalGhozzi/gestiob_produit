<?php

namespace App\Controller;

use App\Entity\Categorie;
use App\Form\CategorieType;
use App\Repository\CategorieRepository;
use Exception;
use PhpParser\Builder\Param;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class CategorieController extends AbstractController
{
    /**
     * @Route("/addCategorie", name="add_categorie")
     */
    public function addCategorie(Request $request): Response
    {
        $em = $this->getDoctrine()->getManager();
        $categorie = new Categorie();
        $form = $this->createForm(CategorieType::class, $categorie);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($categorie);
            $em->flush();
            return $this->redirectToRoute('read_categorie');
        }
        return $this->render('back/categorie/addCategorie.html.twig', array("formCategorie" => $form->createView()));
    }

    /**
     * @Route("/readCategorie",name="read_categorie")
     */
    public function readCategorie()
    {
        $categorie= $this->getDoctrine()->getRepository(Categorie::class)->findAll();

        return $this->render("back/categorie/readCategorie.html.twig",array('tabCategorie'=>$categorie));
    }

    // JSON RESPONSES

    /**
     * @Route("/categorie/json", name="CategorieJsonAction")
     * @throws ExceptionInterface
     */
    public function transport(): Response
    {
        $categorie = $this->getDoctrine()->getManager()
            ->getRepository(Categorie::class)->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($categorie);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/categorie/json/new", name="newCategorieJson", methods={"POST"})
     * @throws Exception
     */
    public function newCategorieJson(Request $request): JsonResponse
    {
        $categorie = new Categorie();

        $em = $this->getDoctrine()->getManager();
        $categorie->setNom($request->get('nom'));
        $categorie->setBoiteVitesse($request->get('boitevitesse'));

        $em->persist($categorie);
        $em->flush();

        return new JsonResponse($categorie);
    }

    /**
     * @Route("/categorie/json/update/{id}", name="updateCategorieJson")
     * @throws Exception
     */
    public function updateCategorieJson(Request $request, $id): JsonResponse
    {
        $em = $this->getDoctrine()->getManager();

        $categorie = $em->getRepository(Categorie::class)->find($id);

        $categorie->setNom($request->get('nom'));
        $categorie->setBoiteVitesse($request->get('boitevitesse'));

        $em->flush();

        return new JsonResponse($categorie);
    }

    /**
     * @Route("/categorie/json/{id}", name="CategorieIdJson")
     * @throws ExceptionInterface
     */
    public function categorieIdJson($id): JsonResponse
    {
        $categorie = $this->getDoctrine()->getManager()
            ->getRepository(Categorie::class)->find($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($categorie);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/categorie/json/delete/{id}", name="deleteCategorieJsonAction")
     * @throws ExceptionInterface
     */
    public function deleteCategorieJsonAction($id): JsonResponse
    {
        $categorie = $this->getDoctrine()
            ->getRepository(Categorie::class)->find($id);
        $this->getDoctrine()->getManager()->remove($categorie);
        $this->getDoctrine()->getManager()->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($categorie);
        return new JsonResponse($formatted);
    }

    // JSON RESPONSES DONE !!!

    /**
     * @Route("/editCategorie/{id}", name="edit_categorie")
     */
    public function editCategorie(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $categorie = $em->getRepository(Categorie::class)->find($id);
        $form = $this->createForm(CategorieType::class, $categorie);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('read_categorie');
        }
        return $this->render('back/categorie/editCategorie.html.twig', array("formCategorie" => $form->createView()));
    }

    /**
     * @Route("/deleteCategorie/{id}",name="delete_categorie")
     */
    public function deleteCategorie($id, CategorieRepository $repository){
        $categorie=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($categorie);
        $em->flush();
        return $this->redirectToRoute('read_categorie');
    }


}
