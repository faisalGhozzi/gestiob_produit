<?php

namespace App\Controller;

use App\Entity\Categorie;
use App\Repository\CategorieRepository;
use Doctrine\ORM\EntityManagerInterface;
//use http\Env\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

class CategorieMobileController extends AbstractController
{
    /**
     * @Route("/categorie/mobile", name="app_categorie_mobile")
     */
    public function index(): Response
    {
        return $this->render('categorie_mobile/index.html.twig', [
            'controller_name' => 'CategorieMobileController',
        ]);
    }

    /**
     * @Route("/liste",name="liste")
     */
    public function readCategorie(CategorieRepository $repo, SerializerInterface $serializer)
    {
        $categorie=$repo->findAll();
        $json=$serializer->serialize($categorie, 'json', ['groups'=>'categorie']);
        dump($categorie);
        die;
    }

    /**
     * @Route("/add",name="add")
     */
    public function createCategorie(Request $request, SerializerInterface $serializer)
    {
        $content=$request->getContent();
        $data=$serializer->deserialize($content, Categorie::class,'json');
        $em = $this->getDoctrine()->getManager();
        $em->persist($data);
        $em->flush();
        return new Response('categorie added');
    }
}
